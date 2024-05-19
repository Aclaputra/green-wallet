package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Repository.RoleRepository;
import fullstuck.green.wallet.Strings.RoleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Autowired
    private RoleServiceImpl roleService;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testRoleService() {
        Role mockRole = Mockito.mock(Role.class, Mockito.RETURNS_DEFAULTS);
        Mockito.when(roleRepository.save(Mockito.any())).thenReturn(mockRole);
        Role role = roleService.getOrSave(RoleEnum.ROLE_USER);
        assertEquals(role, mockRole);
    }
}