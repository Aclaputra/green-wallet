package fullstuck.green.wallet.Model.Response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseWrapper<T> {
    private List<T> data;
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;

    public PageResponseWrapper(Page<T> pages){
        this.data = pages.getContent();
        this.totalElements = pages.getTotalElements();
        this.totalPages = pages.getTotalPages();
        this.page = pages.getNumber();
        this.size = pages.getSize();
    }
}
