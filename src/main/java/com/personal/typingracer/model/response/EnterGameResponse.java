package com.personal.typingracer.model.response;

import com.personal.typingracer.model.GameDetails;
import lombok.Builder;
import lombok.Getter;

/**
 * @author nikhilshinde on 28/09/22
 */
@Getter
public class EnterGameResponse extends BaseResponse<GameDetails> {

    @Builder
    public EnterGameResponse(GameDetails gameDetails, Boolean success, String error) {
        super(success, gameDetails, error);
    }
}
