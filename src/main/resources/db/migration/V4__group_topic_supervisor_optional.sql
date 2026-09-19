-- Groups can now be created before a topic/supervisor is assigned (assigned later via PUT /api/v1/groups/{id}).
ALTER TABLE student_groups ALTER COLUMN topic_id DROP NOT NULL;
ALTER TABLE student_groups ALTER COLUMN supervisor_id DROP NOT NULL;
