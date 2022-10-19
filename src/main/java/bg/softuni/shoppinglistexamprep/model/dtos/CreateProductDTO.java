package bg.softuni.shoppinglistexamprep.model.dtos;

import bg.softuni.shoppinglistexamprep.model.User;
import bg.softuni.shoppinglistexamprep.model.enums.CategoryNameEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateProductDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    @Size(min = 5)
    private String description;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime neededBefore;

    @NotNull
    private CategoryNameEnum category;


    public CreateProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getNeededBefore() {
        return neededBefore;
    }

    public void setNeededBefore(LocalDateTime neededBefore) {
        this.neededBefore = neededBefore;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryNameEnum category) {
        this.category = category;
    }

}
