import Typography from '@mui/material/Typography';
import { Translate } from 'react-jhipster';
import * as React from 'react';

interface Props {
  phoneNumber?: number;
  wallet?: number;
}
const CurrentUserDetails = ({ phoneNumber, wallet }: Props) => {
  return (
    <>
      <Typography variant="h6" gutterBottom>
        <Translate contentKey="telecomProviderApp.applicationUser.phoneNumber">Phone number</Translate>
        {phoneNumber}
      </Typography>
      <Typography variant="h6" gutterBottom>
        <Translate contentKey="telecomProviderApp.applicationUser.wallet">Wallet</Translate>
        {wallet} €
      </Typography>
    </>
  );
};

export default CurrentUserDetails;
