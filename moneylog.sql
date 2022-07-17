DROP TABLE theme_color,
user,
saving_target,
saving,
category,
sub_category,
hidden_sub_category,
transaction,
monthly_transaction;

CREATE TABLE IF NOT EXISTS theme_color(
    theme_color_id bigint UNSIGNED AUTO_INCREMENT,
    theme_color_code text,
    theme_color_gradient_code text,
    PRIMARY KEY(theme_color_id)
);

CREATE TABLE IF NOT EXISTS user(
    user_id varchar(36) NOT NULL,
    user_no bigint UNSIGNED AUTO_INCREMENT,
    email varchar(128) NOT NULL,
    PASSWORD text NOT NULL,
    theme_color_id bigint UNSIGNED NOT NULL DEFAULT 1,
    PRIMARY KEY(user_no),
    FOREIGN KEY theme_color_id(theme_color_id) REFERENCES theme_color(theme_color_id)
);

CREATE TABLE IF NOT EXISTS category (
    category_id bigint UNSIGNED AUTO_INCREMENT,
    category_name varchar(16) NOT NULL,
    PRIMARY KEY(category_id)
);

CREATE TABLE IF NOT EXISTS sub_category (
    sub_category_id bigint UNSIGNED AUTO_INCREMENT,
    user_no bigint UNSIGNED NOT NULL,
    category_id bigint UNSIGNED NOT NULL,
    sub_category_name varchar(16) NOT NULL,
    PRIMARY KEY(sub_category_id),
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no),
    FOREIGN KEY category_id(category_id) REFERENCES category(category_id)
);

CREATE TABLE IF NOT EXISTS hidden_sub_category (
    user_no bigint UNSIGNED NOT NULL,
    sub_category_id bigint UNSIGNED NOT NULL,
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no),
    FOREIGN KEY sub_category_id(sub_category_id) REFERENCES sub_category(sub_category_id)
);

CREATE TABLE IF NOT EXISTS transaction(
    transaction_id bigint UNSIGNED AUTO_INCREMENT,
    user_no bigint UNSIGNED NOT NULL,
    transaction_name varchar(32) NOT NULL,
    amount int NOT NULL,
    transaction_date date NOT NULL,
    category_id bigint UNSIGNED NOT NULL,
    sub_category_id bigint UNSIGNED NOT NULL,
    fixed_flg boolean NOT NULL,
    PRIMARY KEY(transaction_id),
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no),
    FOREIGN KEY category_id(category_id) REFERENCES category(category_id),
    FOREIGN KEY sub_category_id(sub_category_id) REFERENCES sub_category(sub_category_id)
);

CREATE TABLE IF NOT EXISTS saving_target (
    saving_target_id bigint UNSIGNED AUTO_INCREMENT,
    user_no bigint UNSIGNED NOT NULL,
    saving_target_name varchar(32) NOT NULL,
    target_amount int NOT NULL,
    delete_flg boolean NOT NULL,
    PRIMARY KEY(saving_target_id),
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no)
);

CREATE TABLE IF NOT EXISTS saving (
    saving_id bigint UNSIGNED AUTO_INCREMENT,
    user_no bigint UNSIGNED NOT NULL,
    saving_name varchar(32) NOT NULL,
    saving_amount int NOT NULL,
    saving_date date NOT NULL,
    saving_target_id bigint,
    PRIMARY KEY(saving_id),
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no)
);

CREATE TABLE IF NOT EXISTS monthly_transaction (
    monthly_transaction_id bigint UNSIGNED AUTO_INCREMENT,
    user_no bigint UNSIGNED NOT NULL,
    monthly_transaction_name varchar(32) NOT NULL,
    monthly_transaction_amount int NOT NULL,
    monthly_transaction_date int NOT NULL,
    category_id bigint UNSIGNED NOT NULL,
    sub_category_id bigint UNSIGNED NOT NULL,
    include_flg boolean NOT NULL,
    PRIMARY KEY(monthly_transaction_id),
    FOREIGN KEY user_no(user_no) REFERENCES user(user_no),
    FOREIGN KEY category_id(category_id) REFERENCES category(category_id),
    FOREIGN KEY sub_category_id(sub_category_id) REFERENCES sub_category(sub_category_id)
);