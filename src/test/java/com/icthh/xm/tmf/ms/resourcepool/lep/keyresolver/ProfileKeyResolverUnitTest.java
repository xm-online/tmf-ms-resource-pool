package com.icthh.xm.tmf.ms.resourcepool.lep.keyresolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.icthh.xm.tmf.ms.resourcepool.utils.HeaderRequestExtractor;
import org.junit.jupiter.api.Test;

class ProfileKeyResolverUnitTest {

    private final HeaderRequestExtractor headerRequestExtractor = mock(HeaderRequestExtractor.class);
    private final ProfileKeyResolver resolver = new ProfileKeyResolver(headerRequestExtractor);

    @Test
    void appendsProfileHeader() {
        when(headerRequestExtractor.getProfile()).thenReturn("B2C");

        assertThat(resolver.segments(null)).containsExactly("B2C");
    }

    @Test
    void missingProfileIsRejectedAsBefore() {
        when(headerRequestExtractor.getProfile()).thenReturn(null);

        assertThatThrownBy(() -> resolver.segments(null)).isInstanceOf(NullPointerException.class);
    }
}
