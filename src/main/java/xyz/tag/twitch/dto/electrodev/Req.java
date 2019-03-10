package xyz.tag.twitch.dto.electrodev;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tag.twitch.enums.ESwitch;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.dto.electrodev
 * USER      : sean
 * DATE      : 07-Thu-Mar-2019
 * TIME      : 20:00
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Req {
    @SerializedName("switch")
    private ESwitch aSwitch;
}
