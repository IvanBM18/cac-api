package org.modular.cac.services;

import lombok.AllArgsConstructor;
import org.modular.cac.models.GeneralStats;
import org.modular.cac.repositories.GeneralStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class GeneralStatsService {

    private final GeneralStatsRepository generalRepo;

    public Optional<GeneralStats> findByName(String name){
        var result = generalRepo.getStats(name);
        if(result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
