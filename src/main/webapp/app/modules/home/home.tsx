import './home.scss';

import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Typography } from '@mui/material';
import Alert from '@mui/material/Alert';
import { Link } from 'react-router-dom';
import { getEntities } from 'app/entities/mobile-plan/mobile-plan.reducer';
import { getEntity } from 'app/entities/application-user/application-user.reducer';
import MobilePlanCardList from 'app/shared/components/mobile-plan-card-list';
import { IMobilePlan } from 'app/shared/model/mobile-plan.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import CurrentUserMobilePlan from 'app/shared/components/current-user-mobile-plan';
import CurrentUserDetails from 'app/shared/components/current-user-details';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const dispatch = useAppDispatch();
  const mobilePlanList: IMobilePlan[] = useAppSelector(state => state.mobilePlan.entities);
  const applicationUserEntity: IApplicationUser = useAppSelector(state => state.applicationUser.entity);
  let currentMobilePlan: IMobilePlan;
  if (mobilePlanList && applicationUserEntity && applicationUserEntity.chosenMobilePlan) {
    currentMobilePlan = mobilePlanList.filter(mobilePlan => mobilePlan.id === applicationUserEntity.chosenMobilePlan.id)[0];
  }

  const getAllMobilePlans = () => {
    dispatch(
      getEntities({
        unpaged: true,
      }),
    );
  };

  const getApplicationUserByAccountId = (id: number) => {
    dispatch(getEntity(id));
  };

  useEffect(() => {
    getAllMobilePlans();
  }, []);

  useEffect(() => {
    if (account && account.id) {
      getApplicationUserByAccountId(account.id);
    }
  }, [account]);

  return (
    <>
      <Typography variant="h3" component="h1" color="primary" sx={{ my: 3 }}>
        <Translate contentKey="home.title" component={'span'} />
      </Typography>
      {account?.login ? (
        <>
          <Alert severity="success">
            <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
              You are logged in as user {account.login}.
            </Translate>
          </Alert>
          {applicationUserEntity && (
            <CurrentUserDetails phoneNumber={applicationUserEntity.phoneNumber} wallet={applicationUserEntity.wallet} />
          )}
          {currentMobilePlan && <CurrentUserMobilePlan mobilePlan={currentMobilePlan} />}
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
