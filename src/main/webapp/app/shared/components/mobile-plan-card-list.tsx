import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { IMobilePlan } from 'app/shared/model/mobile-plan.model';
import { useMediaQuery, useTheme } from '@mui/material';
import { Translate } from 'react-jhipster';
interface Props {
  cards: IMobilePlan[];
}

const MobilePlanCardList = ({ cards }: Props) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <>
      <Typography variant="h5" sx={{ my: 4 }}>
        <Translate contentKey="telecomProviderApp.mobilePlan.home.title">All mobile plans</Translate>
      </Typography>
      <Grid container rowSpacing={1} columnSpacing={1}>
        {cards.map((card, index) => (
          <Grid size={{ xs: 12, sm: 6, md: 3 }} key={`${card.id}-${index}`}>
            <Card sx={{ borderTop: '5px solid ' + theme.palette.primary.dark }}>
              <CardContent>
                <Box display="flex" flexDirection="column" alignItems="center">
                  <Typography variant="h5">{card.name}</Typography>
                  <Typography variant="h4" sx={{ my: 2 }} color="warning">
                    {card.internetDataInGB} GB
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {card.description}
                  </Typography>
                  <Typography variant="h5" sx={{ my: 1 }} color="primary">
                    {card.price} €*
                  </Typography>
                  <Typography variant="body2" color="textSecondary">
                    <Translate contentKey="telecomProviderApp.mobilePlan.priceDescription">* per 4 weeks</Translate>
                  </Typography>
                  <Button size="medium" variant="contained" fullWidth={isMobile} sx={{ mt: 2, mb: 1 }}>
                    <Translate contentKey="telecomProviderApp.mobilePlan.buttonChoosePlan">Choose this plan</Translate>
                  </Button>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </>
  );
};

export default MobilePlanCardList;
