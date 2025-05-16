package kickstart.Service;

import kickstart.Cart.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Optional<CartItem> findById(Long id);

    Page<CartItem> findAllProductsPageable(Pageable pageable);

}