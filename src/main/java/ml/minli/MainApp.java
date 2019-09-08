package ml.minli;

import ml.minli.util.FileUtil;
import ml.minli.util.MailUtil;

import java.io.File;

public class MainApp {

    public static void main(String[] args) {
        boot();
        while (true) {
            try {
                Thread.sleep(Long.parseLong(MailUtil.properties.getProperty("email.time")));
                File file = FileUtil.getFile("ip.txt");
                String ipText = FileUtil.read(file);
                String ipAddress = MailUtil.getIpAddress();
                if (!ipText.equals(ipAddress)) {
                    FileUtil.write(file, ipAddress);
                    MailUtil.sendEmail("email.change.subject", "email.change.text", ipAddress);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void boot() {
        try {
            File file = FileUtil.getFile("ip.txt");
            String ipText = FileUtil.read(file);
            String ipAddress = MailUtil.getIpAddress();
            if (ipText == null || ipText.isBlank()) {
                FileUtil.write(file, ipAddress);
                MailUtil.sendEmail("email.first.subject", "email.first.text", ipAddress);
            } else {
                if (!ipText.equals(ipAddress)) {
                    MailUtil.sendEmail("email.boot.subject", "email.boot.text", ipAddress);
                    FileUtil.write(file, ipAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
