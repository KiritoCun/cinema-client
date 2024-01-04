package vn.udn.dut.cinema.common.core.utils.ip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.file.FileUtils;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.File;

/**
 * Locating tools based on ip address, offline mode
 * Reference address: <a href="https://gitee.com/lionsoul/ip2region/tree/master/binding/java">Integrate ip2region to realize offline IP address location library</a>
 *
 * @author HoaLD
 */
@Slf4j
public class RegionUtils {

    private static final Searcher SEARCHER;

    static {
        String fileName = "/ip2region.xdb";
        File existFile = FileUtils.file(FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + fileName);
        if (!FileUtils.exist(existFile)) {
            ClassPathResource fileStream = new ClassPathResource(fileName);
            if (ObjectUtil.isEmpty(fileStream.getStream())) {
                throw new ServiceException("RegionUtils initialization failed, reason: IP address database data does not exist!");
            }
            FileUtils.writeFromStream(fileStream.getStream(), existFile);
        }

        String dbPath = existFile.getPath();

        // 1. Load the entire xdb from dbPath to memory.
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            throw new ServiceException("RegionUtils initialization failed, reason: Failed to load content from ip2region.xdb file!" + e.getMessage());
        }
        // 2. Create a fully memory-based query object using the above cBuff.
        try {
            SEARCHER = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            throw new ServiceException("RegionUtils failed to initialize because of: " + e.getMessage());
        }
    }

    /**
     * Get city offline by IP address
     */
    public static String getCityInfo(String ip) {
        try {
            ip = ip.trim();
            // 3. Execute the query
            String region = SEARCHER.search(ip);
            return region.replace("0|", "").replace("|0", "");
        } catch (Exception e) {
            log.error("IP address offline get city exception {}", ip);
            return "Unknown";
        }
    }

}
