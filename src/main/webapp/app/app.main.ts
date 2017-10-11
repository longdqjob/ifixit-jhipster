import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ProdConfig } from './blocks/config/prod.config';
import { IfixitAppModule } from './app.module';

ProdConfig();

console.log("----------------");
if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(IfixitAppModule)
.then((success) => console.log(`Application started ifixit`))
.catch((err) => console.error(err));
