package vslugin.de;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class UserFormValidator {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[А-Яа-яЁёA-Za-z\\s\\-]{1,40}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$");
    private static final Pattern INN_PATTERN =
            Pattern.compile("^\\d{12}$");
    private static final Pattern PASSPORT_PATTERN =
            Pattern.compile("^\\d{10}$");

    public ValidationResult validate(UserForm form) {
        ValidationResult result = new ValidationResult();

        // Имя
        if (form.getName() == null || form.getName().isEmpty()) {
            result.add("name", "Имя не может быть пустым");
        } else if (!NAME_PATTERN.matcher(form.getName()).matches()) {
            result.add("name", "Имя: до 40 символов, без цифр и спецсимволов");
        }

        // Дата рождения старше 16 лет
        if (form.getBirthDate() == null || form.getBirthDate().isEmpty()) {
            result.add("birthDate", "Дата рождения обязательна");
        } else {
            try {
                LocalDate birth = LocalDate.parse(form.getBirthDate());
                int age = Period.between(birth, LocalDate.now()).getYears();
                if (age < 16) {
                    result.add("birthDate", "Возраст должен быть старше 16 лет");
                }
            } catch (DateTimeParseException e) {
                result.add("birthDate", "Неверный формат даты");
            }
        }

        // Email
        if (form.getEmail() == null || form.getEmail().isEmpty()) {
            result.add("email", "Email обязателен");
        } else if (form.getEmail().length() > 64
                || !EMAIL_PATTERN.matcher(form.getEmail()).matches()) {
            result.add("email", "Email: до 64 символов, формат user@domain.zone");
        }

        // Телефон
        if (form.getPhone() == null || form.getPhone().isEmpty()) {
            result.add("phone", "Телефон обязателен");
        } else if (!PHONE_PATTERN.matcher(form.getPhone()).matches()) {
            result.add("phone", "Формат телефона строго такой: +7-XXX-XXX-XX-XX");
        }

        // ИНН
        if (form.getInn() == null || form.getInn().isEmpty()) {
            result.add("inn", "ИНН обязателен");
        } else if (!INN_PATTERN.matcher(form.getInn()).matches()) {
            result.add("inn", "ИНН должен содержать 12 цифр");
        } else if (form.getInn().startsWith("00")) {
            result.add("inn", "ИНН не может начинаться с 00");
        }

        // Паспорт
        if (form.getPassport() == null || form.getPassport().isEmpty()) {
            result.add("passport", "Номер паспорта обязателен");
        } else if (!PASSPORT_PATTERN.matcher(form.getPassport()).matches()) {
            result.add("passport", "Номер паспорта должен содержать 10 цифр");
        } else {
            int series = Integer.parseInt(form.getPassport().substring(0, 2));
            if (series < 1 || series > 99) {
                result.add("passport", "Серия паспорта должна быть в диапазоне 01–99");
            }
        }

        return result;
    }
}
