package JFrame.ui;

public class CodeUtil {
    // 定义包含所有可能字符的字符串
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成4位随机验证码
     * @return 4位随机验证码字符串
     */
    public static String getCode(){
        String code = "";
        for (int i = 0; i < 4; i++) {
            // 从CHARACTERS中随机选择一个字符
            int index = (int)(Math.random() * CHARACTERS.length());
            code += CHARACTERS.charAt(index);
        }
        return code;
    }

    public static boolean confirmCode(String inputCode, String generatedCode) {
        return inputCode.equals(generatedCode);
    }
    }

