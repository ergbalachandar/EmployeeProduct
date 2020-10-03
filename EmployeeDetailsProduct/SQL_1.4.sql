drop database EmployeeProduct;
create database EmployeeProduct;
use EmployeeProduct;

CREATE TABLE `users` (
  `username` varchar(255) PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `password` varchar(255),
  `role` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `country` varchar(255),
  `company_id` varchar(255),
  `active` int
);

CREATE TABLE `employee` (
  `id` varchar(255) PRIMARY KEY,
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
  `company_id` varchar(255),
  `job_role` varchar(255),
  `work_location` varchar(255),
  `department` varchar(255),
  `reporting_person` varchar(255),
  `active` integer,
  `postal_code` varchar(255),
  `bic` varchar(20),
  `iban` varchar(25),
  `date_of_joining` date,
  `marital_status` varchar(25)
);

CREATE TABLE `workpermit_details` (
  `employee_id` varchar(255),
  `workpermit_number` varchar(255) PRIMARY KEY,
  `start_date` date,
  `end_date` date,
  `validity` int,
  `document_name` varchar(255),
  `document_type` varchar(255)
);

CREATE TABLE `workpermit_document_details` (
  `workpermit_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `payslip_details` (
  `payslip_number` varchar(255)  PRIMARY KEY,
  `employee_id` varchar(255),
  `payslip_month` varchar(255),
  `document_name` varchar(255),
  `document_type` varchar(255)
);

CREATE TABLE `payslip_document_details` (
  `payslip_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `passport_details` (
  `employee_id` varchar(255),
  `passport_number` varchar(255) PRIMARY KEY,
  `start_date` date,
  `end_date` date,
  `issue_place` varchar(255),
  `validity` int,
  `birth_place` varchar(255),
  `document_name` varchar(255),
  `document_type` varchar(255)
);

CREATE TABLE `passport_document_details` (
  `passport_number` varchar(255) PRIMARY KEY,
  `document_name` varchar(255),
  `document_data` longblob
);

CREATE TABLE `family_details` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `employee_id` varchar(255),
  `first_name` varchar(255),
  `last_name` varchar(255),
  `relation` int,
  `contact_number` varchar(255)
);

CREATE TABLE `company` (
  `id` varchar(255) PRIMARY KEY,
  `name` varchar(255) UNIQUE,
  `email_id` varchar(255),
  `address1` varchar(255),
  `address2` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `country` varchar(255),
  `contact_number` varchar(255),
  `size` int,
  `active` int,
  `flag` int DEFAULT 0 NOT NULL,
  `postal_code` varchar(25),
  `company_type` varchar(25),
  `vat_number` varchar(25)
);

CREATE TABLE `Audit_Trial` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `updated_by` varchar(255),
  `updated_user` varchar(255),
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_company` varchar(255)
);

ALTER TABLE `users` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `employee` ADD FOREIGN KEY (`updated_by`) REFERENCES `users` (`username`);

ALTER TABLE `employee` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `workpermit_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `workpermit_document_details` ADD FOREIGN KEY (`workpermit_number`) REFERENCES `workpermit_details` (`workpermit_number`);

ALTER TABLE `payslip_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `payslip_document_details` ADD FOREIGN KEY (`payslip_number`) REFERENCES `payslip_details` (`payslip_number`);

ALTER TABLE `passport_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `passport_document_details` ADD FOREIGN KEY (`passport_number`) REFERENCES `passport_details` (`passport_number`);

ALTER TABLE `family_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

DROP INDEX email_id ON employee;

ALTER TABLE employee ADD CONSTRAINT email_id UNIQUE(email_id, active);

-- Changes for making employee ID as String - Starts --

ALTER TABLE `family_details` DROP FOREIGN KEY `family_details_ibfk_1`;

ALTER TABLE `passport_details` DROP FOREIGN KEY `passport_details_ibfk_1`;

ALTER TABLE `payslip_details` DROP FOREIGN KEY `payslip_details_ibfk_1`;

ALTER TABLE `workpermit_details` DROP FOREIGN KEY `workpermit_details_ibfk_1`;

ALTER TABLE `family_details` DROP INDEX `employee_id`;

ALTER TABLE `passport_details` DROP INDEX `employee_id`;

ALTER TABLE `payslip_details` DROP INDEX `employee_id`;

ALTER TABLE `workpermit_details` DROP INDEX `employee_id`;

ALTER TABLE `workpermit_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `family_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `passport_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `payslip_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

ALTER TABLE `users` DROP FOREIGN KEY `users_ibfk_1`;

ALTER TABLE `employee` DROP FOREIGN KEY `employee_ibfk_2`;

ALTER TABLE `users` DROP INDEX `company_id`;

ALTER TABLE `employee` DROP INDEX `company_id`;

ALTER TABLE `users` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

ALTER TABLE `employee` ADD FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

DROP INDEX email_id ON employee;

ALTER TABLE employee ADD CONSTRAINT email_id UNIQUE(email_id, active, company_id);
-- Changes for adding MUsers -- starts --
-- Changes for master login -- begins --

Create TABLE `master_users`(
 `muser_name` varchar(255) PRIMARY KEY DEFAULT '' NOT NULL ,
  `mfirst_name` varchar(255),
  `mlast_name` varchar(255),
  `mpassword` varchar(255),
  `mrole` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  );

-- Changes for getting view of companydetails to MAdmin -- Begins -- 
CREATE VIEW COMPANY_DETAILS_MADM AS
select 
c.id,
c.name,
c.email_id,
c.address1,
c.address2,
c.city,
c.state,
c.country,
c.active,
c.vat_number,
c.postal_code,
c.contact_number, 
c.company_type,
count(*) total
from company c,employee;

-- Changes for getting view of companydetails to MAdmin -- Ends --
