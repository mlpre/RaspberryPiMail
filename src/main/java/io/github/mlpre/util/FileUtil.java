package io.github.mlpre.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    private static final String jarFilePath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    public static String getCurrentConfigFilePath(String configFileName) {
        return new File(jarFilePath).getParentFile().getAbsolutePath() + File.separator + configFileName;
    }

    public static File getFile(String fileName) {
        try {
            File file = new File(getCurrentConfigFilePath(fileName));
            if (!file.exists()) {
                file.createNewFile();
                return new File(getCurrentConfigFilePath(fileName));
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void write(File file, String text) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, false));
            bufferedWriter.write(text);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
