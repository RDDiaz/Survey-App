CREATE TABLE IF NOT EXISTS users
(
  id UUID PRIMARY KEY DEFAULT (uuid_generate_v1()),
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS answer
(
  id UUID PRIMARY KEY DEFAULT (uuid_generate_v1()),
  question_id UUID NOT NULL,
  /*question_id UUID NOT NULL references question(id),*/
  answer_text VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS questionlist
(
  id UUID PRIMARY KEY DEFAULT (uuid_generate_v1()),
  name VARCHAR(255) NOT NULL,
  userID UUID,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS question
(
    id UUID PRIMARY KEY DEFAULT (uuid_generate_v1()),
    question VARCHAR(255) NOT NULL,
    question_list_id VARCHAR(255) NOT NULL,
    answers_id VARCHAR(255),
    date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
--     ,
--     CONSTRAINT fk_question_question_list_id FOREIGN KEY (question_list_id) REFERENCES question_list(id) ON DELETE CASCADE
);
