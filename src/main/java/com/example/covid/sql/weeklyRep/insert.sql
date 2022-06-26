INSERT INTO `covid`.`weeklyrep`
(`dataRep`,
 `casesWeekly`,
 `deathsWeekly`,
 `countryAndTerritory`,
 `geoId`,
 `countryTerritoryCode`,
 `continentExp`)
VALUES ('%s',
           %s,
           %s,
        '%s',
        '%s',
        '%s',
        '%s');