package dcc.tp2.chercheurservice.client;


import dcc.tp2.chercheurservice.module.Enseignant;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENSEIGNANT-SERVICE")
public interface EnseignantRestFeign {

    @GetMapping("/Enseignants/{id}")
    @CircuitBreaker(name = "enseignant",fallbackMethod ="Enseignant_fallbackMethod")
    @Retry(name = "enseignant",fallbackMethod ="Enseignant_fallbackMethod")
    @RateLimiter(name = "enseignant",fallbackMethod ="Enseignant_fallbackMethod")
    Enseignant Enseignant_ByID(@PathVariable Long id);


    default Enseignant Enseignant_fallbackMethod(Long id, Throwable throwable) {
        Enseignant fallbackEnseignant = new Enseignant();
        fallbackEnseignant.setId(id);
        fallbackEnseignant.setNom("Enseignant par défaut");
        fallbackEnseignant.setPrenom("Inconnu");
        fallbackEnseignant.setEmail("default@example.com");

        System.out.println("Fallback déclenché pour l'enseignant avec ID: " + id + ", raison: " + throwable.getMessage());

        return fallbackEnseignant;
    }
}
