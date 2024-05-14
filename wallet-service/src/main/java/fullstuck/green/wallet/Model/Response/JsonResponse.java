package fullstuck.green.wallet.Model.Response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
