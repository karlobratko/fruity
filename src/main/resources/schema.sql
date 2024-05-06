CREATE UNIQUE INDEX IF NOT EXISTS uq_arcode_parcels_cadastral_parcel_name
    ON arcode_parcels (name, cadastral_parcel_fk)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_arcode_parcels_arcode
    ON arcode_parcels (arcode)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_attachment_user_name
    ON attachments (user_fk, name)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_cadastral_parcels_user_name
    ON cadastral_parcels (name, user_fk)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_cadastral_parcels_cadastral_municipality_cadastral_number
    ON cadastral_parcels (cadastral_municipality_fk, cadastral_number)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_equipments_user_name
    ON equipments (user_fk, name)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_realisations_work_employee_start_date_time_end_date_time
    ON realisations (work_fk, employee_fk, start_date_time, end_date_time)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_row_clusters_arcode_parcel_name
    ON row_clusters (name, arcode_parcel_fk)
    WHERE delete_date IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_users_arcode
    ON users (oib)
    WHERE delete_date IS NULL;