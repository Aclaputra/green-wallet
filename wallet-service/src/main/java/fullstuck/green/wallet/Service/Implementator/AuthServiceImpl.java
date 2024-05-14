package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.*;
import fullstuck.green.wallet.Model.Request.RegisterRequest;
import fullstuck.green.wallet.Model.Response.RegisterResponse;
import fullstuck.green.wallet.Repository.MerchantRepository;
import fullstuck.green.wallet.Repository.UserRepository;
import fullstuck.green.wallet.Service.AuthService;
import fullstuck.green.wallet.Service.RoleService;
import fullstuck.green.wallet.Strings.RoleEnum;
import fullstuck.green.wallet.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest req) throws ResponseStatusException {
        System.out.println("req service register: " + req);
        try {
            Role role = roleService.getOrSave(RoleEnum.ROLE_USER);
            System.out.println("get or save");
            Merchant merchant = Merchant.builder().name(req.getUsername()).build();
            Merchant savedMerchant = merchantRepository.save(merchant);
            System.out.println("MErchant: " + merchant);
            System.out.println("saved merhchant: " + savedMerchant);

            User user = User.builder()
                    .merchant(merchant)
                    .name(req.getName())
                    .birthDate(Date.from(Instant.now()))
                    .phone_number("+6278327924")
                    .build();
            User savedUser = userRepository.save(user);
            System.out.println("save test" + user);

            AccountDetails accountDetails = AccountDetails.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .role(role)
                    .user(user)
                    .build();

            System.out.println(accountDetails);

            // TODO: consumer create on endpoint
            return RegisterResponse.builder()
                    .id(savedUser.getId())
                    .build();

        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR CATCH");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }
}
