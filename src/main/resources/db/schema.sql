CREATE UNIQUE INDEX uq_arcode_parcels_cadastral_parcels_names ON arcode_parcels (name, cadastral_parcel_fk) WHERE delete_date IS NULL