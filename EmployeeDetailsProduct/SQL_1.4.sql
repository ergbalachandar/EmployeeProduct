use innodb;

CREATE TABLE `users` (
  `user_name` varchar(255) PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `password` varchar(255),
  `role` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `country` varchar(255),
  `company_id` int,
  `active` int
);

CREATE TABLE `employee` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `email_id` varchar(255) UNIQUE,
  `sex` varchar(255),
  `address1` varchar(255),
  `address2` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `country` varchar(255),
  `contact_number` varchar(255),
  `date_of_birth` date,
  `updated_by` varchar(255),
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `company_id` int,
  `job_role` varchar(255),
  `work_location` varchar(255),
  `department` varchar(255)
);

CREATE TABLE `workpermit_details` (
  `employee_id` int,
  `workpermit_number` varchar(255) PRIMARY KEY,
  `start_date` date,
  `end_date` date,
  `validity` int
);

CREATE TABLE `workpermit_document_details` (
  `workpermit_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `payslip_details` (
  `payslip_number` varchar(255)  PRIMARY KEY,
  `employee_id` int,
  `payslip_month` varchar(255)
);

CREATE TABLE `payslip_document_details` (
  `payslip_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `passport_details` (
  `employee_id` int,
  `passport_number` varchar(255) PRIMARY KEY,
  `start_date` date,
  `end_date` date,
  `issue_place` varchar(255),
  `validity` int
);

CREATE TABLE `passport_document_details` (
  `passport_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `family_details` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `employee_id` int,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `relation` int,
  `contact_number` varchar(255)
);

CREATE TABLE `company` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) UNIQUE,
  `email_id` varchar(255),
  `address1` varchar(255),
  `address2` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `country` varchar(255),
  `contact_number` varchar(255),
  `size` int,
  `active` int
);

CREATE TABLE `Audit_Trial` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `updated_by` varchar(255),
  `updated_user` varchar(255),
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_company` varchar(255)
);

ALTER TABLE `users` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `employee` ADD FOREIGN KEY (`updated_by`) REFERENCES `users` (`user_name`);

ALTER TABLE `employee` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `workpermit_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `workpermit_document_details` ADD FOREIGN KEY (`workpermit_number`) REFERENCES `workpermit_details` (`workpermit_number`);

ALTER TABLE `payslip_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `payslip_document_details` ADD FOREIGN KEY (`payslip_number`) REFERENCES `payslip_details` (`payslip_number`);

ALTER TABLE `passport_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `passport_document_details` ADD FOREIGN KEY (`passport_number`) REFERENCES `passport_details` (`passport_number`);

ALTER TABLE `family_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE employee add column reporting_person varchar(255);

ALTER TABLE employee add column active integer;

DROP INDEX email_id ON employee;

ALTER TABLE employee ADD CONSTRAINT email_id UNIQUE(email_id, active);


