import Typography from '@mui/material/Typography';
import { Translate } from 'react-jhipster';
import * as React from 'react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';

interface Props {
  phoneNumber?: number;
  wallet?: number;
  onButtonClick?: () => void;
}
const CurrentUserDetails = ({ phoneNumber, wallet, onButtonClick }: Props) => {
  return (
    <>
      <Typography variant="h6" gutterBottom>
        <Translate contentKey="telecomProviderApp.applicationUser.phoneNumber">Phone number</Translate>
        {phoneNumber}
      </Typography>
      <Box display="flex" flexDirection="row">
        <Typography variant="h6" sx={{ mr: 4 }}>
          <Translate contentKey="telecomProviderApp.applicationUser.wallet">Wallet</Translate>
          {wallet} €
        </Typography>
        <Button variant="contained" onClick={onButtonClick}>
          <Translate contentKey="telecomProviderApp.applicationUser.topUpButtonMessage">Top up</Translate>
        </Button>
      </Box>
    </>
  );
};

export default CurrentUserDetails;
