package inventory.repository;

import inventory.model.Vat;
import inventory.model.dto.VatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface VatRepository extends JpaRepository<Vat,Integer> {

}
