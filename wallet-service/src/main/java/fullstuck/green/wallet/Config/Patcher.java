package fullstuck.green.wallet.Config;

import fullstuck.green.wallet.Model.Request.UpdateProfileRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher {
    public static void profilePatcher(UpdateProfileRequest existingRequest, UpdateProfileRequest incompleteRequest) throws IllegalAccessException {
        Class<?> updateProfileClass = UpdateProfileRequest.class;
        Field[] updateProfileFields = updateProfileClass.getDeclaredFields();
        System.out.println(updateProfileFields.length);
        for (Field field : updateProfileFields) {
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING REQUEST
            Object value = field.get(incompleteRequest);
            if (value != null) {
                field.set(existingRequest, value);
            }
            field.setAccessible(false);
        }
    }

}
