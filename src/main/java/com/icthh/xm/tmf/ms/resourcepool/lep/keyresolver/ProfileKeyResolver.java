package com.icthh.xm.tmf.ms.resourcepool.lep.keyresolver;

import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.tmf.ms.resourcepool.utils.HeaderRequestExtractor;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Appends the {@code profile} request header to the LEP key, e.g. {@code CreateReservation$$B2C}.
 * xm-commons 5 also looks up the legacy script name ({@code -} to {@code _}, {@code .} to {@code $}),
 * which the xm-commons 2 resolver used to build with {@code translateToLepConvention}.
 */
@Component
@RequiredArgsConstructor
public class ProfileKeyResolver implements LepKeyResolver {

    private final HeaderRequestExtractor headerRequestExtractor;

    @Override
    public List<String> segments(LepMethod method) {
        // as before: the xm-commons 2 translateToLepConvention rejected a missing value
        return List.of(Objects.requireNonNull(headerRequestExtractor.getProfile(), "xmEntitySpecKey can't be null"));
    }
}
