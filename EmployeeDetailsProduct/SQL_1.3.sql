CREATE TABLE `users` (
  `user_name` varchar(255) PRIMARY KEY,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `password` varchar(255),
  `role` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `country` varchar(255),
  `company_id` int,
  `active` int
);

CREATE TABLE `employee` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `email_id` varchar(255),
  `sex` varchar(255),
  `address1` varchar(255),
  `address2` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `country` varchar(255),
  `contact_number` varchar(255),
  `date_of_birth` date,
  `updated_by` varchar(255),
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  `document_data` varchar(255)
);

CREATE TABLE `payslip_details` (
  `payslip_number` int PRIMARY KEY AUTO_INCREMENT,
  `employee_id` int,
  `payslip_month` varchar(255)
);

CREATE TABLE `payslip_document_details` (
  `payslip_id` int PRIMARY KEY,
  `document_data` varchar(255)
);

CREATE TABLE `passport_details` (
  `employee_id` int,
  `passport_number` varchar(255) PRIMARY KEY,
  `start_date` date,
  `end_date` date,
  `issue_place` varchar(255),
  `validity` int
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
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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

ALTER TABLE `family_details` ADD FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

-- Company Table : 
INSERT INTO `company` (`id`, `name`, `email_id`, `address1`, `address2`, `city`, `state`, `country`, `contact_number`, `size`, `active`) VALUES ('2', 'aksdfjlasdk', 'adi.1293@gmail.com', 'Mahalakshmi Nagar', '6th cross street', 'chennai', 'tamilNadu', 'India', '9410', '0', '0');

-- Users Table : 
INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`, `role`, `country`, `company_id`, `active`) VALUES ('adi.1293@gmail.com', 'Balachandar', 'Gopalan', 'Shyamgops@1', '0', 'India', '1', '0');

INSERT INTO `employee` (`id`, `address1`, `address2`, `city`, `contact_number`, `country`, `date_of_birth`, `department`, `email_id`, `first_name`, `job_role`, `last_name`, `repoting_person`, `sex`, `state`, `work_location`, `company_id`) VALUES (2, 'dasdfasd', 'dadafads', 'asdfasdf', 'adfdasf', 'asdfsadf', '1990-08-30', 'asdfsaf', 'asdfadsf', 'adfadsf', 'adfasdf', 'asdfadsf', '1', 'adf', 'adfasdf', 'adfasdf', '1');

-- WorkPermitDetails Table : 
INSERT INTO `workpermit_details` (`employee_id`, `workpermit_number`, `start_date`, `end_date`, `validity`) VALUES (2, 3333333, '2020-01-08', '2023-01-07', 1);

INSERT INTO `workpermit_details` (`employee_id`, `workpermit_number`, `start_date`, `end_date`, `validity`) VALUES (2, 3333333333, '2018-01-08', '2020-01-07', 0);


--  passport_Details Table 
INSERT INTO `passport_details` (`employee_id`, `passport_number`, `start_date`, `end_date`, `issue_place`, `validity`) VALUES (2, 'fgreere', '2011-08-30', '2021-07-31', 'Chennai', '1');

INSERT INTO `passport_details` (`employee_id`, `passport_number`, `start_date`, `end_date`, `issue_place`, `validity`) VALUES (2, 'werereerreewrerwerw', '2001-08-31', '2011-07-31', 'Chennai', '0');

--  workpermit_document_details
INSERT INTO `workpermit_document_details` (`workpermit_number`, `document_name`, `document_data`) VALUES (3333333, 'sadfdfdf', 'sdfsadfdsaffas');

INSERT INTO `workpermit_document_details` (`workpermit_number`, `document_name`, `document_data`) VALUES (3333333333, 'sadfdfdf', 'sdfsadfdsaffas');


INSERT INTO `payslip_details` (`payslip_number`, `payslip_month`, `employee_id`) VALUES (2, 'sdafsadf', 2);

--  payslip_document_details
INSERT INTO `payslip_document_details` (`payslip_number`, `document_data`) VALUES (2, 'adfasdf');

--  family_details
INSERT INTO `family_details` (`employee_id`,`first_name`, `last_name`, `relation`,`contact_number`) VALUES (2, 'asjdfkjds', 'adfasdfds', '0','9884941049');

