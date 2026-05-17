package io.github.rikkakawaii0612.galgame;

public class Resources {
    public static final String OP = of("/audios/op.mp3");
    public static final String START_BGM = of("/audios/start_bgm.mp3");
    public static final String NORMAL_BGM = of("/audios/normal_bgm.mp3");
    public static final String SHERRY_HAIHAI = of("/audios/sherry_haihai.wav");

    private static String of(String path) {
        return String.valueOf(Resources.class.getResource(path));
    }
}
