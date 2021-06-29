import com.sciamus.contractanalyzer.application.CheckReportDTO;
import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.sciamus.contractanalyzer.domain.reporting.idGenerator.ReportIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;

@ExtendWith(MockitoExtension.class)
public class CheckTest {

    @Mock
    private CheckReportBuilder checkReportBuilder;

    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    @Mock
    private ReportRepository reportRepositoryMock;

    private ContractChecksService contractChecksService;

    @BeforeEach
    public void init() {
        final CurrentUserService currentUserService = new CurrentUserService();
        ReportIdGenerator reportIdGenerator = new ReportIdGenerator();
        final ReportService reportService = new ReportService();
        final CheckReportMapper checkReportMapper = new CheckReportMapper();
        final CheckRepository checkRepository = new CheckRepository();
        contractChecksService = new ContractChecksService();
    }

    @Test
    @DisplayName("Creates report")
    public void checkReportCreationTest() throws MalformedURLException {
        //given
        given (keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());

        // when
        CheckReportDTO checkReportDTO = contractChecksService.runAndGetSavedReportWithId("Dummy Check", "http://localhost:1212/dummyUrl");

        // then
        assertThat(checkReportDTO).isNotNull();
    }

    @Test
    @DisplayName("kot")
    public void kot() {
        //given

        // when


        // then
    }
}
