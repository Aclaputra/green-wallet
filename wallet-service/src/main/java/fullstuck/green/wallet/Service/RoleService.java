package fullstuck.green.wallet.Service;

import fullstuck.green.wallet.Model.Entity.Role;
import fullstuck.green.wallet.Strings.RoleEnum;

public interface RoleService {
    Role getOrSave(RoleEnum role);
}
