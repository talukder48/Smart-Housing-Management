package app.hm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.PersonalLoanForm;

public interface PersonalLoanFormRepo extends JpaRepository<PersonalLoanForm, Long>{

}
