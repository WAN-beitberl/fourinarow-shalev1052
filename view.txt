CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `new_view` AS
    SELECT 
        `highschool`.`identification_card` AS `identification_card`,
        `highschool`.`grade_avg` AS `grade_avg`
    FROM
        `highschool`