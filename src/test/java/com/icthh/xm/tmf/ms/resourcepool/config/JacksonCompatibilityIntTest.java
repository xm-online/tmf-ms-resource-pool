package com.icthh.xm.tmf.ms.resourcepool.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.icthh.xm.commons.i18n.error.domain.vm.ParameterizedErrorVM;
import com.icthh.xm.tmf.ms.resourcepool.ResourcepoolApp;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.RelatedPartyRef;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.Reservation;
import com.icthh.xm.tmf.ms.resourcepool.web.v1.api.model.ReservationCreate;
import java.time.OffsetDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Request and response JSON must stay as it was with Jackson 2 / openapi-generator 4 (compared with master).
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TenantConfigMockConfiguration.class, ResourcepoolApp.class})
class JacksonCompatibilityIntTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void modelKeepsDeclarationOrderNullsAndDates() {
        Reservation model = new Reservation();
        model.setId("1");
        model.setValidFor(OffsetDateTime.parse("2021-03-04T05:06:07.123+02:00"));

        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(model));

        assertThat(json.propertyNames()).containsExactly("@baseType", "@schemaLocation", "@type", "description",
            "href", "id", "relatedParty", "reservationState", "valid_for", "reservationItem", "channelRef",
            "requestedPeriod", "productOfferingRef");
        assertThat(json.get("valid_for").asString()).isEqualTo("2021-03-04T05:06:07.123+02:00");
        // optional list: null as the 4.x generator left it
        assertThat(json.get("reservationItem").isNull()).isTrue();
    }

    @Test
    void absentFieldsKeepModelDefaults() {
        ReservationCreate request = jsonMapper.readValue("{\"relatedParty\":{\"id\":\"1\"},\"zzz\":1}",
            ReservationCreate.class);

        assertThat(request.getRelatedParty()).isEqualTo(new RelatedPartyRef().id("1"));
        assertThat(request.getReservationItem()).isNull();
    }

    @Test
    void businessErrorKeepsPropertyOrder() {
        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(
            new ParameterizedErrorVM("error.code", "message", Map.of())));

        assertThat(json.propertyNames()).containsExactly("error", "error_description", "requestId", "params");
    }
}
