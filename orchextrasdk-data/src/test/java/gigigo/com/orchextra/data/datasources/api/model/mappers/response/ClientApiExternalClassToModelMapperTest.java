package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;

import gigigo.com.orchextra.data.datasources.builders.ApiClientAuthDataBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ClientApiExternalClassToModelMapperTest {

    private ClientApiExternalClassToModelMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ClientApiExternalClassToModelMapper();
    }

    @Test
    public void testDataToModelOk() throws Exception {
        Date expectedValue = new Date(Calendar.getInstance().getTimeInMillis()+ ApiClientAuthDataBuilder.EXPIRES_IN);

        ApiClientAuthData apiClientAuthData = ApiClientAuthDataBuilder.Builder().build();

        ClientAuthData clientAuthData = mapper.externalClassToModel(apiClientAuthData);

        assertNotNull(clientAuthData);
        assertEquals(ApiClientAuthDataBuilder.VALUE, clientAuthData.getValue());
        assertEquals(ApiClientAuthDataBuilder.PROJECT_ID, clientAuthData.getProjectId());
        assertEquals(ApiClientAuthDataBuilder.USER_ID, clientAuthData.getUserId());
        assertEquals(ApiClientAuthDataBuilder.EXPIRES_IN, clientAuthData.getExpiresIn());
        assertThat(expectedValue, isDateEqualTo(clientAuthData.getExpiresAt()));
    }

    @Test
    public void testDataToModelNullDate() throws Exception {
        ApiClientAuthData apiClientAuthData = ApiClientAuthDataBuilder.Builder().build();

        ClientAuthData clientAuthData = mapper.externalClassToModel(apiClientAuthData);

        assertNotNull(clientAuthData);
        assertEquals(ApiClientAuthDataBuilder.VALUE, clientAuthData.getValue());
        assertEquals(ApiClientAuthDataBuilder.PROJECT_ID, clientAuthData.getProjectId());
        assertEquals(ApiClientAuthDataBuilder.USER_ID, clientAuthData.getUserId());
        assertEquals(ApiClientAuthDataBuilder.EXPIRES_IN, clientAuthData.getExpiresIn());
        assertNotNull(clientAuthData.getExpiresAt());
    }
}
