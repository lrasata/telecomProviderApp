import { IMobilePlan } from 'app/shared/model/mobile-plan.model';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Translate } from 'react-jhipster';
import * as React from 'react';
import { Gauge } from '@mui/x-charts';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

interface Props {
  mobilePlan: IMobilePlan;
}
const CurrentUserMobilePlan = ({ mobilePlan }: Props) => {
  return (
    <Card sx={{ minWidth: 300, my: 2 }}>
      <CardContent>
        <Typography variant="h5">{mobilePlan.name}</Typography>
        <Typography variant="body2" color="text.secondary">
          {mobilePlan.description}
        </Typography>
        <Typography variant="h6" color="textSecondary">
          {mobilePlan.price} € <Translate contentKey="telecomProviderApp.mobilePlan.priceDescription">* per 4 weeks</Translate>
        </Typography>
        <Grid container rowSpacing={1} columnSpacing={3}>
          <Grid size={{ xs: 12, sm: 6, md: 6 }}>
            <Box display="flex" flexDirection="column" sx={{ justifyContent: 'center', alignItems: 'center' }}>
              <Typography variant="h6" sx={{ my: 2 }} color="primary">
                <Translate contentKey="telecomProviderApp.mobilePlan.internetDataInGB">Internet Data</Translate>
              </Typography>
              <Gauge width={200} height={200} text={`max ${mobilePlan.internetDataInGB} GB`} value={90} />
            </Box>
          </Grid>
          <Grid size={{ xs: 12, sm: 6, md: 6 }}>
            <Box display="flex" flexDirection="column" sx={{ justifyContent: 'center', alignItems: 'center' }}>
              <Typography variant="h6" sx={{ my: 2 }} color="primary">
                <Translate contentKey="telecomProviderApp.mobilePlan.unlimitedSmsAndCalls">Sms and calls</Translate>
              </Typography>
              <Gauge width={200} height={200} text={mobilePlan.unlimitedSmsAndCalls ? 'Unlimited' : 'Limited'} value={100} />
            </Box>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default CurrentUserMobilePlan;
