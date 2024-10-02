package org.modular.cac.endpoints;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modular.cac.models.GeneralStats;
import org.modular.cac.services.GeneralStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/stats")
@Tag(name = "Stats",description = "API for General CAC Stats")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StatsController {

    private final GeneralStatsService generalService;

    @GetMapping("/{name}")
    public GeneralStats getByName(@PathVariable("name")String name){
        return generalService.findByName(name).orElse(new GeneralStats());
    }
}
