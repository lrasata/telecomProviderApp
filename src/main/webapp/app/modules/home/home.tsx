import './home.scss';

import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Typography } from '@mui/material';
import Alert from '@mui/material/Alert';
import { Link } from 'react-router-dom';
import { getEntities } from 'app/entities/mobile-plan/mobile-plan.reducer';
import MobilePlanCardList from 'app/shared/components/mobile-plan-card-list';
import { IMobilePlan } from 'app/shared/model/mobile-plan.model';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const dispatch = useAppDispatch();
  const mobilePlanList: IMobilePlan[] = useAppSelector(state => state.mobilePlan.entities);

  const getAllMobilePlans = () => {
    dispatch(
      getEntities({
        unpaged: true,
      }),
    );
  };

  useEffect(() => {
    getAllMobilePlans();
  }, []);

  return (
    <>
      <Typography variant="h3" component="h1" color="primary">
        <Translate contentKey="home.title" component={'span'} />
      </Typography>
      {account?.login ? (
        <>
          <Alert severity="success">
            <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
              You are logged in as user {account.login}.
            </Translate>
          </Alert>
          <MobilePlanCardList cards={mobilePlanList} />
        </>
      ) : (
        <>
          <Alert severity="warning">
            <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

            <Link to="/login" className="alert-link">
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </Link>
            <Translate contentKey="global.messages.info.authenticated.suffix">
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
              <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
            </Translate>
          </Alert>
        </>
      )}
    </>
  );
};

export default Home;
