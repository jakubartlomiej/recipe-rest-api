package recipes.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @Timestamp
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank
    private String description;
    @ElementCollection
    @NotEmpty
    private List<String> ingredients;
    @ElementCollection
    @NotEmpty
    private List<String> directions;
    @JsonIgnore
    private String author;
}
