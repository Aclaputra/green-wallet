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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest req) throws ResponseStatusException {
        try {
            Role role = roleService.getOrSave(RoleEnum.ROLE_USER);
            Merchant merchant = Merchant.builder().name(req.getUsername()).build();
            Merchant savedMerchant = merchantRepository.save(merchant);

            Date birthDate = DateUtils.parseDate(req.getBirthDate(),
                    new String[] { "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy" });
            User user = User.builder()
                    .merchant(merchant)
                    .name(req.getName())
                    .birthDate(birthDate)
                    .phone_number(req.getPhoneNumber())
                    .build();
            User savedUser = userRepository.save(user);

            AccountDetails accountDetails = AccountDetails.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .role(role)
                    .user(user)
                    .build();

            return RegisterResponse.builder()
                    .id(savedUser.getId())
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
