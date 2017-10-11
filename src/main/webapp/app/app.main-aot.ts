import { platformBrowser } from '@angular/platform-browser';
import { ProdConfig } from './blocks/config/prod.config';
import { IfixitAppModuleNgFactory } from '../../../../target/aot/src/main/webapp/app/app.module.ngfactory';

ProdConfig();

platformBrowser().bootstrapModuleFactory(IfixitAppModuleNgFactory)
.then((success) => console.log(`Application started ifits`))
.catch((err) => console.error(err));
