import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Translate } from 'react-jhipster';
import Grid from '@mui/material/Grid';
import * as React from 'react';

interface Props {
  phoneNumber?: number;
  wallet?: number;
}
const CurrentUserDetails = ({ phoneNumber, wallet }: Props) => {
  return (
    <Card sx={{ minWidth: 300, my: 2 }}>
      <CardContent>
        <Grid container rowSpacing={1} columnSpacing={3}>
          <Grid size={{ xs: 12, sm: 6, md: 6 }}>
            <Typography variant="h6" sx={{ my: 2 }}>
              <Translate contentKey="telecomProviderApp.applicationUser.phoneNumber">Phone number</Translate>
              {phoneNumber}
            </Typography>
          </Grid>
          <Grid size={{ xs: 12, sm: 6, md: 6 }}>
            <Typography variant="h6" sx={{ my: 2 }}>
              <Translate contentKey="telecomProviderApp.applicationUser.wallet">Wallet</Translate>
              {wallet} €
            </Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default CurrentUserDetails;
