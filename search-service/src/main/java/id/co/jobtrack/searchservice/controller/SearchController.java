package id.co.jobtrack.searchservice.controller;

import id.co.jobtrack.searchservice.dto.request.ApplicationSearch;
import id.co.jobtrack.searchservice.service.ApplicationSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/applications")
@RequiredArgsConstructor
public class SearchController {

    private final ApplicationSearchService applicationSearchService;

    @GetMapping
    public ResponseEntity<?> search(@ModelAttribute ApplicationSearch request) {
        return ResponseEntity.ok(applicationSearchService.search(request));
    }
}
