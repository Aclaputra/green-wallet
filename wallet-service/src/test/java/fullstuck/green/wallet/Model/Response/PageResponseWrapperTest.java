package fullstuck.green.wallet.Model.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

class PageResponseWrapperTest {

    @Test
    void testPageResponseWrapper() {
        PageResponseWrapper<Object> pageResponseWrapper = PageResponseWrapper.builder()
                .page(1)
                .totalPages(3)
                .data(new ArrayList<>())
                .size(10)
                .totalElements(5L)
                .build();
        PageResponseWrapper<Object> pageResponseWrapperGetSet = new PageResponseWrapper<>();
        pageResponseWrapperGetSet.setTotalPages(pageResponseWrapper.getTotalPages());
        pageResponseWrapperGetSet.setPage(pageResponseWrapper.getPage());
        pageResponseWrapperGetSet.setData(pageResponseWrapper.getData());
        pageResponseWrapperGetSet.setSize(pageResponseWrapper.getSize());
        pageResponseWrapperGetSet.setTotalElements(pageResponseWrapper.getTotalElements());
        Assertions.assertNotNull(pageResponseWrapperGetSet);
    }
}