package com.example.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.example.demo.repository.IslandRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Island;
import com.example.demo.repository.entity.Workstation;
import com.example.demo.service.exceptions.NotFoundException;
import com.example.demo.service.stereotype.Business;

@Business
public class IslandService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IslandRepository islandRepository;

    public void alocarWorkstationDisponivel(@NonNull Integer userId) {

        final var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException());

        final var islands = islandRepository.findIslandWithAvailableWorkstations();

        if (islands.isEmpty()) {
            throw new IllegalStateException("Workstations not available");
        }

        // busca ilhas começando por uma ws livre, depois duas, ...
        Island freeIsland = islands.getFirst();
        for (int slots = 1; slots < Island.Disposition.CIRCULAR.getPlacements(); slots++) {
            final int positions = slots;
            var possibleIsland = islands.stream()
                    .filter(i -> i.getWorkstations().stream()
                            .map(Workstation::getUser)
                            .filter(Objects::nonNull)
                            .count() == positions)
                    .findFirst();
            if (possibleIsland.isPresent()) {
                freeIsland = possibleIsland.get();
                break;
            }
        }

        // primeira workstation livre e seta o usuário
        freeIsland.getWorkstations().stream()
                .filter(ws -> ws.getUser() == null)
                .findFirst()
                .ifPresent(ws -> ws.setUser(user));

        islandRepository.save(freeIsland);
    }

}
