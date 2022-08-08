package ca.bc.gov.backendstartapi.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExampleDTO {

    private Long id;
    private String firstName;
    private String lastName;

    public String getStringProps() {
        StringBuilder builder = new StringBuilder();
        if (!Objects.isNull(id)) {
            builder.append("id: ").append(id);
        }
        if (!Objects.isNull(firstName)) {
            builder.append(", firstName: '").append(firstName).append("'");
        }
        if (!Objects.isNull(lastName)) {
            builder.append(", lastName: '").append(lastName).append("'");
        }

        return builder.toString();
    }
}
