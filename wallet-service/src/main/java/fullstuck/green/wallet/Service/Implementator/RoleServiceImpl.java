package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Repository.RoleRepository;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Strings.RoleEnum;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(RoleEnum role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);
        /**
         * kalau ada balikin dari database
         */
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        /**
         * kaalu ada kita buat baru
         */
        Role currentRole = Role.builder()
                .name(role)
                .build();

        return roleRepository.saveAndFlush(currentRole);
    }
}
