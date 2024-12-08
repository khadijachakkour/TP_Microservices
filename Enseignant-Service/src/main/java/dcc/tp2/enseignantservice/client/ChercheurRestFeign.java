package dcc.tp2.enseignantservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CHERCHEUR-SERVICE")
public interface ChercheurRestFeign {

    @GetMapping("/Chercheurs/Enseignant/{id}")
    @CircuitBreaker(name = "count-chercheur",fallbackMethod ="Chercheur_fallbackMethod")
    @Retry(name = "count-chercheur",fallbackMethod ="Chercheur_fallbackMethod")
    @RateLimiter(name = "count-chercheur",fallbackMethod ="Chercheur_fallbackMethod")
    Long nb_chercheur_Enseignant(@PathVariable Long id);

    default Long Chercheur_fallbackMethod(Long id, Throwable throwable) {
        System.out.println("Fallback déclenché pour le nombre de chercheurs pour l'enseignant avec ID: " + id + ", raison: " + throwable.getMessage());

        return 0L;
    }
}
