package xyz.tag.twitch.constant;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.constant
 * USER      : sean
 * DATE      : 07-Thu-Mar-2019
 * TIME      : 20:12
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
public class Constants {
    public static final String REST_ELECTRO_DEV_ENDPOINT = "http://192.168.0.3:8083/api/v1";
    public static final String SCHEMA_NAME = "twitch";

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Instantiation Not Permitted...");
    }
}
